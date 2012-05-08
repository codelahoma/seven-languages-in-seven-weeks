require 'pry'

class Tree
  attr_accessor :children, :node_name

  def initialize(tree_hash)
    @children ||= []
    @node_name = tree_hash.first[0]
    tree_hash[@node_name].each do |k,v|
      @children << Tree.new({ k => v})
    end

  end


  def visit_all(&block)
    visit &block
    children.each {|e| e.visit_all &block}
  end

  def visit(&block)
    block.call self
  end
end

ruby_tree = Tree.new({ 'ruby' => { 'MacRuby' => {}, 'JRuby' => {}}})

puts "Visiting a node"
ruby_tree.visit {|node| puts node.node_name}
puts
puts "Visiting entire tree"
ruby_tree.visit_all {|node| puts node.node_name}
